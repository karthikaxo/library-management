package com.example.libraryapi.dto.bookItem;

import com.example.libraryapi.entity.BookItemStatus;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookItemUpdate {

    @Positive
    private Long libraryId;

    private String barcode;

    private BookItemStatus status;
}
