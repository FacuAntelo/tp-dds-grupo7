package controllers;

import models.DTO.DatosDeActividadDTO;
import models.Organizacion.DatosDeActividad;
import repositories.RepositorioDA;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministradorController {
    RepositorioDA repositorioDA = new RepositorioDA();

    public ModelAndView homeAdministrador(Request request, Response response) {
        List<DatosDeActividad> datosDeActividadList= repositorioDA.buscarTodos();
        List<DatosDeActividadDTO> datosDeActividadDTOList =new ArrayList<>();

        datosDeActividadList.forEach(da->{
            DatosDeActividadDTO datosDeActividadDTO=new DatosDeActividadDTO(da.getId(),
                    da.getActividad(),
                    da.getTipoDeConsumo(),
                    Double.toString(da.getFactorDeEmision().getValorFactorEmision()).concat(da.getFactorDeEmision().getUnidad())
                    );
            datosDeActividadDTOList.add(datosDeActividadDTO);


        });


        return new ModelAndView(new HashMap<String, Object>(){{
            put("datosDeActividad", datosDeActividadDTOList);
        }},"administrador/.....hbs");
    }

    public Response modificarFactorDeEmision(Request request, Response response){
        DatosDeActividad datosDeActividadAModificar= repositorioDA.buscar(Integer.valueOf(request.params("idDatoActividad")));
        double valorNuevoFactorEmision = Double.valueOf(request.queryParams("nuevoValorFE"));

        return response;
    }
}
