package pe.edu.upeu.PharmaBackend.auditoria;
import org.slf4j.MDC;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public record EventoAuditoria(String usuario,String accion,String entidad,Long entidadId,String detalle,String requestId) {
 public static EventoAuditoria de(String accion,String entidad,Long id,String detalle){
 Authentication a=SecurityContextHolder.getContext().getAuthentication();
 String usuario=a==null || !a.isAuthenticated() || a instanceof AnonymousAuthenticationToken ? "sistema":a.getName();
 return new EventoAuditoria(cortar(usuario,50),accion,entidad,id,cortar(detalle,500),MDC.get("requestId"));
 }
 public static EventoAuditoria login(String accion,String usuario){return new EventoAuditoria(cortar(usuario,50),accion,"Usuario",null,null,MDC.get("requestId"));}
 private static String cortar(String valor,int max){return valor==null?null:valor.substring(0,Math.min(max,valor.length()));}
}
