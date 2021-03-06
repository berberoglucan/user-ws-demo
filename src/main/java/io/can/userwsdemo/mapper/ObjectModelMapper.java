package io.can.userwsdemo.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component("mapper")
public class ObjectModelMapper {

    private final ModelMapper modelMapper;

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <D, T> List<D> mapAll(Collection<T> sourceCollection, Class<D> destinationType) {
        return sourceCollection.stream().map(source -> map(source, destinationType)).collect(Collectors.toList());
    }

}
