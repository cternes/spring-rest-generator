package ${targetPackage};

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ${fullyQualifiedModel};
import ${fullyQualifiedRepo};

@RestController
@RequestMapping("${endpoint}")
public class ${clazz} {

    private ${repo} repo;

    @Autowired
    public ${clazz}(${repo} repo) {
        this.repo = repo;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ${model} create${model}(@Valid @RequestBody ${model} entity) {
        return repo.add(entity);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ${model} get${model}s() {
        return repo.get${model}s();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Transactional
    public ${model} update${model}(@PathVariable(value="id") Long id,
            @Valid ${model} entity) {
        throwIfEntityCouldNotBeFoundWithId(id);
        
        entity.setId(id);
        
        return repo.update(entity);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public void delete${model}(@PathVariable(value="id") Long id) {
        throwIfEntityCouldNotBeFoundWithId(id);
        
        repo.deleteById(id);
    }

    private ${model} throwIfEntityCouldNotBeFoundWithId(Long id) {
        return repo.getById(id).orElseThrow(() -> ResourceNotFoundWebException.throwIt("${model}", id));
    }
}
