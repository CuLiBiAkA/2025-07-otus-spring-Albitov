package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("genre")
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    private String id;

    private String name;
}
