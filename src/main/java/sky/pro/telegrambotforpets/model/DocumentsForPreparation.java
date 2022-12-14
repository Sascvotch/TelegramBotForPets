package sky.pro.telegrambotforpets.model;

import javax.persistence.*;
import java.util.Objects;
/**
 * Сущность <b>DocumentsForPreparation</b> содержит информацию о правилах, советах
 * и рекомендациях для подготовки к встрече с питомцем. ИНформация содержится в файлах,
 * ссылки на которые хранятся в БД <br>
 * Отображается на таблицу <b>documentsForPreparation</b>
 */
@Entity
@Table(name = "documents_for_preparation")
public class DocumentsForPreparation {

    @Id
    @Column (name = "documents_for_preparation_id")
    /**
     * убрал Gene {@GeneratedValue}, т.к. в БД использую автоинкрементирующийся тип данных
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "documents_for_preparation_description")
    private String description;

    @Column(name = "documents_for_preparation_file_path")
    private String filePath;
    @Column(name = "documents_for_preparation_media_type")
    private String mediaType;
    @Column(name = "documents_for_preparation_file_size")
    private Long fileSize;

    @Column(name = "documents_for_preparation_kind_of_animal")
    private String kindOfAnimal;

    public String getKindOfAnimal() {
        return kindOfAnimal;
    }

    public void setKindOfAnimal(String kindOfAnimal) {
        this.kindOfAnimal = kindOfAnimal;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentsForPreparation that = (DocumentsForPreparation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DocumentsForPreparation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", filePath='" + filePath + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", fileSize=" + fileSize +'\'' +
                ", kindOfAnimal=" + kindOfAnimal +
                '}';
    }
}