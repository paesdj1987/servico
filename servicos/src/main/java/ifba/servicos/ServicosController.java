package ifba.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
public class ServicosController {
	
	 @Autowired
	 private ServicoRepository servicoRepository;
	
	 @GetMapping("/hello")
	    public String Hello(){
	        return "Hello API";
	    }
	 

	    @PostMapping
	    public @ResponseBody Servico addNewServico (@RequestBody Servico servico) {
	        return servicoRepository.save(servico);
	    }

	    @GetMapping
	    public @ResponseBody Iterable<Servico> getAllServicos() {
	        return servicoRepository.findAll();
	    }
	    
	    @GetMapping(path="/{nome}")
	    public @ResponseBody ResponseEntity<Servico> getServicoByNome(@PathVariable String nome) {
	        Optional<Servico> servico = servicoRepository.findByNome(nome);
	        if (servico.isPresent()) {
	            return ResponseEntity.ok(servico.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @PutMapping("/{id}")
	    public @ResponseBody ResponseEntity<Servico> updateServico(@PathVariable Long id, @RequestBody Servico updatedServico) {
	        return servicoRepository.findById(id)
	            .map(servico -> {
	                servico.setNome(updatedServico.getNome());
	                servico.setDescricao(updatedServico.getDescricao());
	                Servico savedServico = servicoRepository.save(servico);
	                return ResponseEntity.ok(savedServico);
	            })
	            .orElseGet(() -> {
	                updatedServico.setId(id);
	                Servico savedServico = servicoRepository.save(updatedServico);
	                return ResponseEntity.ok(savedServico);
	            });
	    }
	   

	    @DeleteMapping(path="/{id}")
	    public @ResponseBody ResponseEntity<String> deleteServico(@PathVariable Long id) {
	        if (servicoRepository.existsById(id)) {
	            servicoRepository.deleteById(id);
	            return ResponseEntity.ok("Servico deleted");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
