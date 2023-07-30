package com.likhith.jpa.pagination.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import com.likhith.jpa.pagination.demo.dto.CustomerInfo;
import com.likhith.jpa.pagination.demo.entity.CustomerEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface JpaPaginationMapper {

	List<CustomerEntity> mapToCustomerEntity(List<CustomerInfo> customerInfo);

	List<CustomerInfo> mapToCustomerInfo(List<CustomerEntity> customerEntity);

}