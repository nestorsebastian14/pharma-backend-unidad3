package pe.edu.upeu.PharmaBackend.auditoria;
import org.slf4j.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.*;
import pe.edu.upeu.PharmaBackend.service.service.AuditoriaService;
@Component public class AuditoriaListener {
 private static final Logger log=LoggerFactory.getLogger(AuditoriaListener.class);
 private final AuditoriaService service;
 public AuditoriaListener(AuditoriaService service){this.service=service;}
 @TransactionalEventListener(phase=TransactionPhase.AFTER_COMMIT)
 public void confirmar(EventoAuditoria evento){try{service.registrar(evento);}catch(RuntimeException ex){log.error("Error de auditoría {}",evento.accion(),ex);}}
}
