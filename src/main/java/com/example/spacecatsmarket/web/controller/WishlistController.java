package com.example.spacecatsmarket.web.controller;


import com.example.spacecatsmarket.domain.wishlist.WishlistEntry;
import com.example.spacecatsmarket.dto.whishlist.WishlistEntryDto;
import com.example.spacecatsmarket.service.interfaces.WishlistService;
import com.example.spacecatsmarket.web.mapper.WishlistEntryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/{customerId}/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final WishlistEntryMapper wishlistEntryMapper;

    @PostMapping
    public ResponseEntity<Void> addToWishlist(
            @PathVariable Long customerId,
            @RequestBody @Valid WishlistEntryDto wishlistEntryDto) {
        WishlistEntry wishlistEntry = wishlistEntryMapper.toWishlistEntry(wishlistEntryDto);
        wishlistService.addToWishlist(customerId, wishlistEntry);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
            @PathVariable Long customerId,
            @PathVariable String productId) {
        wishlistService.removeFromWishlist(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WishlistEntryDto>> getWishlist(@PathVariable Long customerId) {
        List<WishlistEntryDto> wishlist = wishlistService.getWishlist(customerId).stream()
                .map(wishlistEntryMapper::toWishlistEntryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(wishlist);
    }
}
