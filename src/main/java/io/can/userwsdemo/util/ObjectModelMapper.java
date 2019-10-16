package io.can.userwsdemo.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ObjectModelMapper {

    private final ModelMapper modelMapper;

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public <D, T> List<D> mapAll(Collection<T> sourceCollection, Class<D> destinationType) {
        return sourceCollection.stream().map(entity -> map(entity, destinationType)).collect(Collectors.toList());
    }

}
