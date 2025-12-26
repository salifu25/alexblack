package com.maamefashion.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "collection_pieces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String image;

    private String price;

    @Builder.Default
    private Boolean showPrice = false;

    @Builder.Default
    private Boolean available = true;

    @Builder.Default
    private Boolean isVisible = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Category {
        READY_TO_WEAR("ready-to-wear"),
        COUTURE("couture"),
        ACCESSORIES("accessories");

        private final String value;

        Category(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static Category fromValue(String value) {
            for (Category category : values()) {
                if (category.value.equalsIgnoreCase(value)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Unknown category: " + value);
        }

        // Helper method for database queries
        public static Category fromString(String text) {
            for (Category category : Category.values()) {
                if (category.name().equalsIgnoreCase(text) ||
                        category.value.equalsIgnoreCase(text)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
}