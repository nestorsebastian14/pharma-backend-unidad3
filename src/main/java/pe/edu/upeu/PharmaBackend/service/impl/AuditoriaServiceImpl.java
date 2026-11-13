package pe.edu.upeu.PharmaBackend.service.impl;
import java.time.*;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import pe.edu.upeu.PharmaBackend.auditoria.EventoAuditoria;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.entity.AuditoriaEvento;
import pe.edu.upeu.PharmaBackend.repository.AuditoriaEventoRepository;
import pe.edu.upeu.PharmaBackend.service.service.AuditoriaService;
import pe.edu.upeu.PharmaBackend.util.PaginacionUtil;
@Service public class AuditoriaServiceImpl implements AuditoriaService {
 private final AuditoriaEventoRepository repository;
 public AuditoriaServiceImpl(AuditoriaEventoRepository repository){this.repository=repository;}
 @Override @Transactional(propagation=Propagation.REQUIRES_NEW)
 public void registrar(EventoAuditoria evento){
 AuditoriaEvento a=new AuditoriaEvento(); a.setFecha(LocalDateTime.now());a.setUsuario(evento.usuario());
 a.setAccion(evento.accion());a.setEntidad(evento.entidad());a.setEntidadId(evento.entidadId());
 a.setDetalle(evento.detalle());a.setRequestId(evento.requestId());repository.save(a);
 }
 @Override @Transactional(readOnly=true)
 public PaginaResponseDTO<AuditoriaEventoDTO> buscar(String usuario,String accion,LocalDate desde,LocalDate hasta,int pagina,int tamanio){
 Pageable pageable=PaginacionUtil.construir(pagina,tamanio,"fecha","desc",Set.of("fecha","usuario","accion"),"fecha");
 return PaginaResponseDTO.de(repository.buscar(vacio(usuario),vacio(accion),desde==null?null:desde.atStartOfDay(),
 hasta==null?null:hasta.plusDays(1).atStartOfDay(),pageable).map(AuditoriaEventoDTO::de));
 }
 private String vacio(String s){return s==null||s.isBlank()?null:s.trim();}
}
