package info.smartfactory.domain.amr.service;

import org.mapstruct.Mapper;

import info.smartfactory.domain.amr.entity.Amr;

@Mapper(componentModel = "spring")
public interface AmrMapper {

	AmrDto toDto(Amr amr);

	Amr toEntity(AmrDto amrDto);

}
