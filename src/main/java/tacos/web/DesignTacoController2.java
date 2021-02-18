package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class DesignTacoController2 {

    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController2(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(path="/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<Taco>> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        CollectionModel<Taco> tacoCollectionModel = CollectionModel.of(tacos);
        tacoCollectionModel.add(
                linkTo(methodOn(DesignTacoController2.class).recentTacos())
                        .withRel("recents"));
        return new ResponseEntity<>(tacoCollectionModel, HttpStatus.OK);
    }
}
