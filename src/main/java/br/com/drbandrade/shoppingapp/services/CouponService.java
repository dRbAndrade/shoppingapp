package br.com.drbandrade.shoppingapp.services;

import br.com.drbandrade.shoppingapp.dtos.CouponDTO;
import br.com.drbandrade.shoppingapp.models.Coupon;
import br.com.drbandrade.shoppingapp.repositories.CouponRepository;
import br.com.drbandrade.shoppingapp.services.exceptions.CouponException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {


    private CouponRepository repository;

    @Autowired
    public CouponService(CouponRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CouponDTO persistNew(CouponDTO dto){
        Coupon entity = repository.save(new Coupon(dto));
        return new CouponDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<CouponDTO> findPaged(Pageable pageable){
        return repository.findAll(pageable).map(CouponDTO::new);
    }

    @Transactional(readOnly = true)
    public CouponDTO findById(long id){
        Coupon entity = repository.findById(id).orElseThrow(
                ()->new CouponException(String.format("No Coupon with id: %d was found",id))
        );
        return new CouponDTO(entity);
    }

    @Transactional(readOnly = true)
    public CouponDTO findByName(String name){
        Coupon entity = repository.findByName(name).orElseThrow(
                ()->new CouponException(String.format("No Coupon with name: %s was found",name))
        );
        return new CouponDTO(entity);
    }

    @Transactional
    public CouponDTO update(long id,CouponDTO dto){
        Coupon entity = repository.getById(id);
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        entity = repository.save(entity);
        return new CouponDTO(entity);

    }

    @Transactional
    public void delete(long id){
        repository.deleteById(id);

    }

}
