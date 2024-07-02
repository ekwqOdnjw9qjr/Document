package ru.lenazavupach.documentzxc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.lenazavupach.documentzxc.dto.CompanyDto;
import ru.lenazavupach.documentzxc.entity.Company;
import ru.lenazavupach.documentzxc.exception.CurrentException;
import ru.lenazavupach.documentzxc.exception.ErrorType;
import ru.lenazavupach.documentzxc.repository.CompanyRepository;
import ru.lenazavupach.documentzxc.repository.DocumentRepository;
import ru.lenazavupach.documentzxc.service.CompanyService;
import ru.lenazavupach.documentzxc.service.mapper.CompanyMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company company;
    private CompanyDto companyDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
        company.setAddress("Test Address");
        company.setEmail("test@example.com");
        company.setPhoneForCalls(1234567890L);
        company.setInn(9876543210L);

        companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setName("Test Company");
        companyDto.setAddress("Test Address");
        companyDto.setEmail("test@example.com");
        companyDto.setPhoneForCalls(1234567890L);
        companyDto.setInn(9876543210L);
    }

    @Test
    void testGetAll() {
        when(companyRepository.findAll()).thenReturn(Arrays.asList(company));
        when(companyMapper.toListDto(Arrays.asList(company))).thenReturn(Arrays.asList(companyDto));

        List<CompanyDto> companyDtos = companyService.getAll();

        assertThat(companyDtos).hasSize(1);
        assertThat(companyDtos.get(0)).isEqualTo(companyDto);

        verify(companyRepository, times(1)).findAll();
        verify(companyMapper, times(1)).toListDto(Arrays.asList(company));
    }

    @Test
    void testGetById() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyMapper.toDto(company)).thenReturn(companyDto);

        CompanyDto result = companyService.getById(1L);

        assertThat(result).isEqualTo(companyDto);

        verify(companyRepository, times(1)).findById(1L);
        verify(companyMapper, times(1)).toDto(company);
    }

    @Test
    void testGetById_NotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getById(1L))
                .isInstanceOf(CurrentException.class)
                .hasMessageContaining("Company with id: 1 not found");

        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        when(companyMapper.toEntity(companyDto)).thenReturn(company);

        companyService.create(companyDto);

        verify(companyMapper, times(1)).toEntity(companyDto);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testUpdate() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyMapper.toEntity(companyDto)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDto(company)).thenReturn(companyDto);

        CompanyDto updatedCompany = companyService.update(companyDto, 1L);

        assertThat(updatedCompany).isEqualTo(companyDto);

        verify(companyRepository, times(1)).findById(1L);
        verify(companyMapper, times(1)).toEntity(companyDto);
        verify(companyRepository, times(1)).save(company);
        verify(companyMapper, times(1)).toDto(company);
    }

    @Test
    void testDelete() {
        companyService.delete(1L);

        verify(companyRepository, times(1)).deleteById(1L);
    }
}
