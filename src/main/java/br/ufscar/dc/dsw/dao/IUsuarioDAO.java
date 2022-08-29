package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
	
	Usuario save(Usuario usuario);
	Usuario findById(long id);
	Usuario findByCPF(String CPF);
	Usuario findByEmail(String email);
	List<Usuario> findAll();
	void deleteById(Long id);
	
    @Query("SELECT u FROM Usuario u WHERE u.nome = :username")
    public Usuario getUserByUsername(@Param("username") String username);

}