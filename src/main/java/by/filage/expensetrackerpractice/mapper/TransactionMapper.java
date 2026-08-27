package by.filage.expensetrackerpractice.mapper;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction toEntity(TransactionRequest request);
    TransactionResponse toResponse(Transaction transaction);
    List<TransactionResponse> toResponseList(List<Transaction> transactions);
}
