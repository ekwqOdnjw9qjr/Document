package ru.lenazavupach.documentzxc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.lenazavupach.documentzxc.dto.DocumentDto;
import ru.lenazavupach.documentzxc.entity.Document;
import ru.lenazavupach.documentzxc.exception.CurrentException;
import ru.lenazavupach.documentzxc.exception.ErrorType;
import ru.lenazavupach.documentzxc.repository.CompanyRepository;
import ru.lenazavupach.documentzxc.repository.DocumentRepository;
import ru.lenazavupach.documentzxc.service.DocumentService;
import ru.lenazavupach.documentzxc.service.mapper.DocumentMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private DocumentService documentService;

    private Document document;
    private DocumentDto documentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setType("Test Type");
        document.setDate(LocalDate.now());
        document.setAuthor("Test Author");

        documentDto = new DocumentDto();
        documentDto.setId(1L);
        documentDto.setTitle("Test Document");
        documentDto.setType("Test Type");
        documentDto.setDate(LocalDate.now());
        documentDto.setAuthor("Test Author");
    }

    @Test
    void testGetById() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentMapper.toDto(document)).thenReturn(documentDto);

        DocumentDto result = documentService.getById(1L);

        assertThat(result).isEqualTo(documentDto);

        verify(documentRepository, times(1)).findById(1L);
        verify(documentMapper, times(1)).toDto(document);
    }

    @Test
    void testGetById_NotFound() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> documentService.getById(1L))
                .isInstanceOf(CurrentException.class)
                .hasMessageContaining("Document with id: 1 not found");

        verify(documentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByAuthor() {
        when(documentRepository.findDocumentByAuthor("Test Author")).thenReturn(Arrays.asList(document));
        when(documentMapper.toListDto(Arrays.asList(document))).thenReturn(Arrays.asList(documentDto));

        List<DocumentDto> documents = documentService.findByAuthor("Test Author");

        assertThat(documents).hasSize(1);
        assertThat(documents.get(0)).isEqualTo(documentDto);

        verify(documentRepository, times(1)).findDocumentByAuthor("Test Author");
        verify(documentMapper, times(1)).toListDto(Arrays.asList(document));
    }

    @Test
    void testCreate() {
        when(documentRepository.findByUniqueAttributes(
                documentDto.getTitle(),
                documentDto.getType(),
                documentDto.getDate(),
                documentDto.getAuthor(),
                documentDto.getNumber()
        )).thenReturn(Optional.empty());
        when(documentMapper.toEntity(documentDto)).thenReturn(document);

        documentService.create(documentDto);

        verify(documentMapper, times(1)).toEntity(documentDto);
        verify(documentRepository, times(1)).save(document);
    }

    @Test
    void testCreate_Duplicate() {
        when(documentRepository.findByUniqueAttributes(
                documentDto.getTitle(),
                documentDto.getType(),
                documentDto.getDate(),
                documentDto.getAuthor(),
                documentDto.getNumber()
        )).thenReturn(Optional.of(document));

        assertThatThrownBy(() -> documentService.create(documentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Document with the same attributes already exists");

        verify(documentMapper, never()).toEntity(any(DocumentDto.class));
        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    void testUpdate() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentMapper.toEntity(documentDto)).thenReturn(document);
        when(documentRepository.save(document)).thenReturn(document);
        when(documentMapper.toDto(document)).thenReturn(documentDto);

        DocumentDto updatedDocument = documentService.update(documentDto, 1L);

        assertThat(updatedDocument).isEqualTo(documentDto);

        verify(documentRepository, times(1)).findById(1L);
        verify(documentMapper, times(1)).toEntity(documentDto);
        verify(documentRepository, times(1)).save(document);
        verify(documentMapper, times(1)).toDto(document);
    }

    @Test
    void testDelete() {
        documentService.delete(1L);

        verify(documentRepository, times(1)).deleteById(1L);
    }
}
