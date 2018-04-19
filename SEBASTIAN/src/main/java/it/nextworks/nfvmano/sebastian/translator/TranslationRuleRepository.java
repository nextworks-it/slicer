package it.nextworks.nfvmano.sebastian.translator;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.nextworks.nfvmano.sebastian.catalogue.elements.VsdNsdTranslationRule;

public interface TranslationRuleRepository extends JpaRepository<VsdNsdTranslationRule, Long> {

	List<VsdNsdTranslationRule> findByVsbId(String vsbId);
	
}
