package ro.uvt.fmi.itro.ejb.utils;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

public class ValidatorUtil {
	private final ValidatorFactory factory;
	 
    public ValidatorUtil() {
        factory = Validation.buildDefaultValidatorFactory();
    }
  
    public< T > void validate( final T instance ) {  
        final Validator validator = factory.getValidator();
  
        final Set< ConstraintViolation< T > > violations = validator
            .validate( instance, Default.class );

        String msg = "";
        if( !violations.isEmpty() ) {
            final Set< ConstraintViolation< ? > > constraints = 
                new HashSet< ConstraintViolation< ? > >( violations.size() );

            for ( final ConstraintViolation< ? > violation: violations ) {
            	
            	msg+=violation.getMessage();
                constraints.add( violation );
            }
   
            throw new ConstraintViolationException(msg,  constraints );
        }
    }
}
