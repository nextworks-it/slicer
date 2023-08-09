package it.nextworks.nfvmano.catalogue.repos;

import it.nextworks.nfvmano.catalogue.plugins.vim.VIM;
import it.nextworks.nfvmano.catalogue.plugins.vim.VIMType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VIMRepository extends JpaRepository<VIM, Long> {

    Optional<VIM> findByVimId(String vimId);

    List<VIM> findByVimType(VIMType manoType);
}
