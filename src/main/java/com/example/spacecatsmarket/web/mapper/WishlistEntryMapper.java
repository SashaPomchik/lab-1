package com.example.spacecatsmarket.web.mapper;

import com.example.spacecatsmarket.domain.wishlist.WishlistEntry;
import com.example.spacecatsmarket.dto.whishlist.WishlistEntryDto;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistEntryMapper {

    @Mapping(target = "productId", source = "wishlistEntry.productId")
    @Mapping(target = "notifiedWhenAvailable", source = "wishlistEntry.notifiedWhenAvailable")
    WishlistEntryDto toWishlistEntryDto(WishlistEntry wishlistEntry);

    @Mapping(target = "productId", source = "wishlistEntryDto.productId")
    @Mapping(target = "notifiedWhenAvailable", source = "wishlistEntryDto.notifiedWhenAvailable")
    WishlistEntry toWishlistEntry(@Valid WishlistEntryDto wishlistEntryDto);
}
