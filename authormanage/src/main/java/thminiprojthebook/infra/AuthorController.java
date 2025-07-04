package thminiprojthebook.infra;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import thminiprojthebook.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/authors")
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @RequestMapping(
        value = "/authors/{id}/approve",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Author approve(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /author/approve  called #####");
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        optionalAuthor.orElseThrow(() -> new Exception("No Entity Found"));
        Author author = optionalAuthor.get();
        author.approve();

        authorRepository.save(author);
        return author;
    }

    @RequestMapping(
        value = "/authors/{id}/disapprove",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Author disApprove(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /author/disApprove  called #####");
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        optionalAuthor.orElseThrow(() -> new Exception("No Entity Found"));
        Author author = optionalAuthor.get();
        author.disApprove();

        authorRepository.save(author);
        return author;
    }

     @GetMapping("/authors/admin")
    public List<Author> getUnapprovedAuthors() {
        List<Author> unapprovedAuthors = authorRepository.findByIsApprovedFalse();
        return unapprovedAuthors;
    }
}
//>>> Clean Arch / Inbound Adaptor
