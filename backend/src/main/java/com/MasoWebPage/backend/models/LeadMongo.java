package com.MasoWebPage.backend.models;
import com.MasoWebPage.backend.api.dto.lead.LeadDTOIn;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter//{
@Setter//       anotacoes do lombok sao apenas para simplificar codigo, nao afeta comportamento!!!!!!
@AllArgsConstructor//
@NoArgsConstructor//}
@Document("leads")
public class LeadMongo {

    @Id
    private String id;
    @Email
    private String email;


    public LeadMongo(LeadDTOIn lead) {
        if(lead.email() != null && !lead.email().isBlank()){
            this.email = lead.email();
        }
    }
}
