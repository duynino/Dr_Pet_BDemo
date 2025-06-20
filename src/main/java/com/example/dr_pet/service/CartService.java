package com.example.dr_pet.service;

import com.example.dr_pet.DTO.Request.AddToCartRequest;
import com.example.dr_pet.DTO.Response.AddToCartResponse;
import com.example.dr_pet.model.*;
import com.example.dr_pet.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dr_pet.DTO.Request.RemoveFromCartRequest;
import com.example.dr_pet.DTO.Response.RemoveFromCartResponse;
import com.example.dr_pet.model.CartDetail;

import com.example.dr_pet.DTO.Request.OrderRequest;
import com.example.dr_pet.DTO.Response.OrderResponse;
import com.example.dr_pet.repo.VoucherRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class CartService {

    @Autowired
    private VoucherRepo voucherRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private DetailOrderRepo detailOrderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartDetailRepo cartDetailRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ProductRepo productRepo;

    public AddToCartResponse addToCart(String username, AddToCartRequest request) {
        Account account = accountRepo.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 1. Kiểm tra cart đã tồn tại chưa
        Cart cart = cartRepo.findByAccount(account);
        if (cart == null) {
            cart = new Cart();
            cart.setAccount(account);
            cart.setCreatedAt(LocalDateTime.now());
            cart.setIsCheckedOut(false);
            cart = cartRepo.save(cart);
        }

        // 2. Kiểm tra sản phẩm
        Product product = productRepo.findByProductIDAndIsActiveTrue(request.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 3. Kiểm tra cart detail đã có sản phẩm này chưa
        CartDetail existingDetail = cartDetailRepo.findCartDetailByCart(cart).stream()
                .filter(cd -> cd.getProduct().getProductID().equals(product.getProductID()))
                .findFirst()
                .orElse(null);

        if (existingDetail != null) {
            // Nếu đã có, tăng số lượng
            existingDetail.setQuantity(existingDetail.getQuantity() + request.getQuantity());
            cartDetailRepo.save(existingDetail);
        } else {
            // Nếu chưa có, tạo mới
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(request.getQuantity());
            cartDetail.setPrice(product.getPrice());
            cartDetailRepo.save(cartDetail);
        }

        // Đếm tổng số lượng sản phẩm trong cart
        List<CartDetail> cartDetails = cartDetailRepo.findCartDetailByCart(cart);
        int totalItems = cartDetails.stream().mapToInt(CartDetail::getQuantity).sum();

        return new AddToCartResponse(
                cart.getCartID(),
                product.getProductID(),
                request.getQuantity(),
                totalItems,
                "Thêm vào giỏ hàng thành công"
        );
    }

    public RemoveFromCartResponse removeFromCart(String username, RemoveFromCartRequest request) {
        Account account = accountRepo.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Cart cart = cartRepo.findByAccount(account);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartDetail> cartDetails = cartDetailRepo.findByCartAndProduct_ProductID(cart, request.getProductID());
        if (cartDetails.isEmpty()) {
            throw new RuntimeException("Sản phẩm không có trong giỏ hàng");
        }

        int quantityToRemove = request.getQuantity();
        int removed = 0;

        for (CartDetail detail : cartDetails) {
            int q = detail.getQuantity();
            if (quantityToRemove <= 0) break;
            if (q <= quantityToRemove) {
                cartDetailRepo.delete(detail);
                removed += q;
                quantityToRemove -= q;
            } else {
                detail.setQuantity(q - quantityToRemove);
                cartDetailRepo.save(detail);
                removed += quantityToRemove;
                quantityToRemove = 0;
            }
        }

        if (removed == 0) {
            throw new RuntimeException("Không thể xóa số lượng yêu cầu");
        }

        return new RemoveFromCartResponse(request.getProductID(), removed, "Đã xóa sản phẩm khỏi giỏ hàng");
    }

    public OrderResponse checkoutCart(String username, OrderRequest request) {
        Account account = accountRepo.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Cart cart = cartRepo.findByAccount(account);
        if (cart == null) throw new RuntimeException("Cart not found");

        List<CartDetail> cartDetails = cartDetailRepo.findCartDetailByCart(cart);
        if (cartDetails.isEmpty()) throw new RuntimeException("Giỏ hàng trống");

        // Tính tổng tiền
        float total = 0f;
        for (CartDetail cd : cartDetails) {
            float price = cd.getPrice() != null ? cd.getPrice() : 0f;
            int quantity = cd.getQuantity() != null ? cd.getQuantity() : 0;
            total += price * quantity;
        }

        // Áp dụng voucher nếu có
        Voucher voucher = null;
        float discount = 0f;
        if (request.getVoucherID() != null) {
            voucher = voucherRepo.findById(request.getVoucherID())
                    .orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));
            discount = voucher.getDiscount() != null ? voucher.getDiscount() : 0f;
        }

        // Tạo Order
        Order order = new Order();
        order.setAccount(account);
        order.setVoucher(voucher);
        order.setTotalPrice(total);
        order.setDiscountPrice(discount);
        order.setOrderStatus("PENDING");
        order.setCreateDate(LocalDate.now()); // Sửa ở đây
        order.setNote(request.getNote());
        order = orderRepo.save(order);

        // Tạo DetailOrder cho từng sản phẩm
        for (CartDetail cd : cartDetails) {
            DetailOrder detail = new DetailOrder();
            detail.setOrder(order);
            detail.setProduct(cd.getProduct());
            detail.setQuantity(cd.getQuantity());
            detail.setPrice(cd.getPrice());
            detailOrderRepo.save(detail);
        }

        // Xóa hết cartDetail (làm trống giỏ hàng)
        cartDetailRepo.deleteAll(cartDetails);

        return new OrderResponse(order.getOrderID(), total, discount, "Đặt hàng thành công");
    }
}

