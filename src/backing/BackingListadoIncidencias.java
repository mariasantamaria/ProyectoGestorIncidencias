package backing;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entidades.Incidencia;
import services.IncidenciasService;
import services.RolesService;
import util.PaginacionHelper;

@Named
@ViewScoped
public class BackingListadoIncidencias implements Serializable{
	ExternalContext usuarioEc= FacesContext.getCurrentInstance().getExternalContext();
	private static final long serialVersionUID = 1072552360870918593L;
	Incidencia i=new Incidencia();
	private PaginacionHelper paginacion;
	private int slctnrpag = 5;
	private String nombreusuario=usuarioEc.getRemoteUser();
	private Long idrol=0L;
	List<Incidencia>listadoIncidencias=null;
	List<Incidencia>listadoIncidenciasRango=null;
	private String tipoBusqueda="%";
	@EJB
	 IncidenciasService incidenciaService;
	@EJB
	 RolesService rolService;
	
	public BackingListadoIncidencias() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void ini() {
		if (paginacion == null) {
			paginacion = new PaginacionHelper(getSlctnrpag(), 0) {
				@Override
				public long getItemsCount() {
					return incidenciaService.getTotalPorTipo(tipoBusqueda,nombreusuario );
				}
			};
		}
		
	//System.out.println("id rol del usuario es"+idrol);
		listadoIncidencias = incidenciaService.listadoIncidenciasPorUsuario(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag(),tipoBusqueda, nombreusuario);
	}
	public PaginacionHelper getPaginacion() {
		return paginacion;
	}
	public void setPaginacion(PaginacionHelper paginacion) {
		this.paginacion = paginacion;
	}
	/*getters and setter*/
	
	public List<Incidencia> getListadoIncidencias() {
		return listadoIncidencias;
	}
	public Long getIdrol() {
		return idrol;
	}
	public void setIdrol(Long idrol) {
		this.idrol = idrol;
	}
	public String getNombreusuario() {
		return nombreusuario;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public void setListadoIncidencias(List<Incidencia> listadoIncidencias) {
		this.listadoIncidencias = listadoIncidencias;
	}
	public List<Incidencia> getlistadoIncidenciasRango() {
		return listadoIncidenciasRango;
	}
	public void setlistadoIncidenciasRango(List<Incidencia> listadoIncidenciasRango) {
		this.listadoIncidenciasRango = listadoIncidenciasRango;
	}
	public IncidenciasService getincidenciaService() {
		return incidenciaService;
	}
		
	public void setincidenciaService(IncidenciasService incidenciaService) {
		this.incidenciaService = incidenciaService;
	}
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	
	public int getSlctnrpag() {
		return slctnrpag;
	}
	public void setSlctnrpag(int slctnrpag) {
		this.slctnrpag = slctnrpag;
	}
	
	/* METODOS DEL LISTADO **/
	public long getTotalInc() {
		resetPaginacion();
		return incidenciaService.getTotalPorTipo(tipoBusqueda, nombreusuario);
	}
	
	public int getTotalIncRango() {
		resetPaginacion();
		return (listadoIncidencias != null) ? listadoIncidencias.size() : 0;
	}

	public void paginaAnterior() {
	
		paginacion.getPaginaAnterior();
		listadoIncidencias = incidenciaService.listadoIncidenciasPorUsuario(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda,nombreusuario);
	
		
	}

	public void paginaSiguiente() {
		
		paginacion.getPaginaSiguiente();
		listadoIncidencias = incidenciaService.listadoIncidenciasPorUsuario(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda,nombreusuario);
		
	}

	public void resetPaginacion() {
		
		int nuevapagina = (paginacion.getPrimerElementoPagina() / slctnrpag);
		paginacion.setNrpag(slctnrpag);
		paginacion.setPagina(nuevapagina);
		listadoIncidencias = incidenciaService.listadoIncidenciasPorUsuario(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),tipoBusqueda,nombreusuario);
	}
	public String editarIncidencia() {
		return "/user/editarIncidencia.xhtml&facesredirect=true";
	}
	/**************************************************************************/
	public void getListadoIncidenciasRango() {
		paginacion=null;
		idrol=rolService.getRolPorNombre(nombreusuario);
		ini();
	}
	/**************************************************/
	
}
