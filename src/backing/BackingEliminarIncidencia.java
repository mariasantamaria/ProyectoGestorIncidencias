package backing;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.RollbackException;

import entidades.Incidencia;
import services.IncidenciasService;
import util.PaginacionHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class BackingEliminarIncidencia implements Serializable {
	private String estadocerrada = "5";
	private String estadorechazada = "6";
	private PaginacionHelper paginacion;
	private int slctnrpag = 5;
	Incidencia i=new Incidencia();
	List<Incidencia>listadoIncidencias=null;
	List<Incidencia>listadoIncidenciasPag=null;
	@EJB
	 IncidenciasService inService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -783564389793496759L;

	public BackingEliminarIncidencia() {
		// TODO Auto-generated constructor stub
	}
	/***********************metodos listado********************************/
	@PostConstruct
	public void ini() {
		if (paginacion == null) {
			paginacion = new PaginacionHelper(getSlctnrpag(), 0) {
				@Override
				public long getItemsCount() {
					return inService.getTotalFiltroEliminar(estadocerrada,estadorechazada);
				}
			};
		}
		listadoIncidencias = inService.listadoIncidenciasEliminar(paginacion.getPagina() * paginacion.getNrpag(),paginacion.getNrpag(),estadocerrada,estadorechazada);
	}
	public int getTotalIncFil() {
		resetPaginacion();
		return (listadoIncidencias != null) ? listadoIncidencias.size() : 0;
	}
	public long getTotalInc() {
		resetPaginacion();
		return inService.getTotalFiltroEliminar(estadocerrada,estadorechazada);
	}

	public void paginaAnterior() {
	
		paginacion.getPaginaAnterior();
		listadoIncidencias = inService.listadoIncidenciasEliminar(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),estadocerrada,estadorechazada);
	
		
	}

	public void paginaSiguiente() {
		
		paginacion.getPaginaSiguiente();
		listadoIncidencias = inService.listadoIncidenciasEliminar(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),estadocerrada,estadorechazada);
		
	}


	public void resetPaginacion() {
		
		int nuevapagina = (paginacion.getPrimerElementoPagina() / slctnrpag);
		paginacion.setNrpag(slctnrpag);
		paginacion.setPagina(nuevapagina);
		listadoIncidencias = inService.listadoIncidenciasEliminar(paginacion.getPagina() * paginacion.getNrpag(),
				paginacion.getNrpag(),estadocerrada,estadorechazada);
	}
	
	public void getEstadoIncidenciaFiltrada() {
		paginacion=null;
		ini();
	}
	/********************eliminar***************************************/
	public void eliminarIncidencia(Long idincidencia) {
			try {
				inService.eliminarIncidencia(idincidencia);
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle archivomensajes = ResourceBundle.getBundle("resources.application",
						context.getViewRoot().getLocale());
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								archivomensajes.getString("generico.registroCreadoConExito"),
								archivomensajes.getString("generico.registroCreadoConExito")));
			//volver a listar
			paginacion=null;
			ini();
			}catch (RollbackException e) {
				e.printStackTrace();
			} catch (Exception e) {
				String mensaje = e.getCause().getCause().getMessage();
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
			}
			
		}

	/****************getter ans setters********************************/

	public String getEstadocerrada() {
		return estadocerrada;
	}

	public void setEstadocerrada(String estadocerrada) {
		this.estadocerrada = estadocerrada;
	}

	public String getEstadorechazada() {
		return estadorechazada;
	}

	public void setEstadorechazada(String estadorechazada) {
		this.estadorechazada = estadorechazada;
	}
	public PaginacionHelper getPaginacion() {
		return paginacion;
	}
	public void setPaginacion(PaginacionHelper paginacion) {
		this.paginacion = paginacion;
	}
	public int getSlctnrpag() {
		return slctnrpag;
	}
	public void setSlctnrpag(int slctnrpag) {
		this.slctnrpag = slctnrpag;
	}
	public Incidencia getI() {
		return i;
	}
	public void setI(Incidencia i) {
		this.i = i;
	}
	public List<Incidencia> getListadoIncidencias() {
		return listadoIncidencias;
	}
	public void setListadoIncidencias(List<Incidencia> listadoIncidencias) {
		this.listadoIncidencias = listadoIncidencias;
	}
	public List<Incidencia> getListadoIncidenciasPag() {
		return listadoIncidenciasPag;
	}
	public void setListadoIncidenciasPag(List<Incidencia> listadoIncidenciasPag) {
		this.listadoIncidenciasPag = listadoIncidenciasPag;
	}
	
	

}
