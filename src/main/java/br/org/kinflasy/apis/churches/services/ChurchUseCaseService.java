package br.org.kinflasy.apis.churches.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.churches.dto.ChurchDto;
import br.org.kinflasy.libs.churches.dto.ChurchRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChurchUseCaseService {

    private ChurchService churchService;
    private UnitService unitService;

    private ModelMapper mapper;

    public ChurchDto.Starter createStarter(final ChurchRequest.Starter request) {
        final var church = churchService.create(request);
        final var unit = unitService.create(church.getId(), request.getUnit());

        return mapper.map(church, ChurchDto.Starter.class)
                .setUnit(unit);
    }

}
