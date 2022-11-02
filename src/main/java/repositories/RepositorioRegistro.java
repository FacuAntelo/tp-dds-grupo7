package repositories;

import models.DTO.RegistroDTO;
import models.HuellaDeCarbono.RegistroHC;
import models.db.EntityManagerHelper;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioRegistro {

    public List<RegistroDTO> getRegistroHCDTOListDeOrganizacion(int id){
        List<RegistroHC> registroHCList = EntityManagerHelper.getEntityManager().createQuery("" +
                "select r from Organizacion as o join o.registrosHC as r " +
                "where o.id="+ id,RegistroHC.class).getResultList();
        return registroHCList.stream().map(r -> new RegistroDTO(r.getFecha(),r.getTipoRegistro(),r.getValorHCDatoActividad().getValor(),r.getValorHCTrayecto().getValor()
                , r.getValorHCTotal().getValor())).collect(Collectors.toList());
    }
}
