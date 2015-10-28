package io.probedock.demo.jeestack.rest;

import io.probedock.demo.jeestack.persistence.OperationLogDao;
import io.probedock.demo.jeestack.service.CalculationException;
import io.probedock.demo.jeestack.service.CalculatorService;
import io.probedock.demo.jeestack.to.ErrorTO;
import io.probedock.demo.jeestack.to.OperationTO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST resource for {@link io.probedock.demo.jeestack.service.CalculatorService}.
 *
 * @author Laurent Prevost <laurent.prevost@probeodock.io>
 */
@Path("calculator")
@Stateless
public class CalculatorResource {
	@EJB
	protected CalculatorService calculatorService;

	@EJB
	protected OperationLogDao operationLogDao;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doOperation(OperationTO operation){
		try {
			return Response.ok(calculatorService.process(operation)).build();
		}
		catch (CalculationException ce) {
			return Response.status(422).entity(new ErrorTO(ce.getMessage())).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.status(200).entity(operationLogDao.findAll()).build();
	}
}
