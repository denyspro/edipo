package br.com.edipo.ada.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import br.com.edipo.ada.entity.Resultado;
import br.com.edipo.ada.util.PersistenciaUtil;

public class ResultadoSB {

	private static final Logger log = Logger.getLogger(ResultadoSB.class.getName());

	@SuppressWarnings("unchecked")
	public static List<Resultado> getResultado(Integer idAvaliacao, Integer idUsuario) {
		String jpql = "select new br.com.edipo.ada.entity.Resultado (et.dsEtiqueta, avg(es.vlEscolha) as vlCalculado) "
			+	"from Escolha es "
			+	"join es.resolucao re "
			+	"join es.alternativa al "
			+	"join al.questao qu "
			+	"join qu.etiquetas et "
			+	"where es.blSelecionada = 1 "
			+	"and re.avaliacao.id = :idAvaliacao "
			+	"and re.idUsuario = :idUsuario "
			+	"group by et.dsEtiqueta";
		List <Resultado> resultados = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Resultado.class);
		query.setParameter("idAvaliacao", idAvaliacao);
		query.setParameter("idUsuario", idUsuario);

		try {
			resultados = (List<Resultado>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resultados;
	}

	@SuppressWarnings("unchecked")
	public static List<Resultado> getResultado(Integer idAvaliacao) {
		String jpql = "select new br.com.edipo.ada.entity.Resultado (et.dsEtiqueta, avg(es.vlEscolha) as vlCalculado) "
			+	"from Escolha es "
			+	"join es.resolucao re "
			+	"join es.alternativa al "
			+	"join al.questao qu "
			+	"join qu.etiquetas et "
			+	"where es.blSelecionada = 1 "
			+	"and re.avaliacao.id = :idAvaliacao "
			+	"group by et.dsEtiqueta";
		List <Resultado> resultados = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Resultado.class);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			resultados = (List<Resultado>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resultados;
	}

	public static Long getNrResolucoes(Integer idAvaliacao) {
		String jpql = "select count(r) from Resolucao r where r.avaliacao.id = :idAvaliacao";
		Long nrResolucoes = 0L;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			nrResolucoes = (Long) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return nrResolucoes;
	}

	public static Long getNrInscritos(Integer idAvaliacao) {
		String jpql = "select count(distinct i.idUsuario) "
			+	"from Avaliacao a "
			+	"join a.cursos c "
			+	"join c.inscritos i "
			+	"where a.id = :idAvaliacao";
		Long nrInscritos = 0L;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			nrInscritos = (Long) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return nrInscritos;
	}

	public static BigDecimal getIndiceAcerto(Integer idResolucao, Integer idQuestao) {
		String jpql = "select sum(es.vlEscolha) * ( sum( case when es.vlEscolha > 0.00 then 1 else 0 end ) / count(es) ) "
			+	"from Escolha es "
			+	"join es.alternativa al "
			+	"join al.questao qu "
			+	"where es.blSelecionada = 1 "
			+	"and es.resolucao.id = :idResolucao "
			+	"and qu.id = :idQuestao "
			+	"group by qu";
		BigDecimal vlIndiceAcerto = BigDecimal.ZERO;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idResolucao", idResolucao);
		query.setParameter("idQuestao", idQuestao);

		log.info(String.format("getIndiceAcerto: %d, %d", idResolucao, idQuestao));

		try {
			vlIndiceAcerto = (BigDecimal) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return vlIndiceAcerto;
	}
}
