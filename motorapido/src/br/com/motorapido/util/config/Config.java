package br.com.motorapido.util.config;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import br.com.minhaLib.util.EntityManagerUtil;




public class Config implements ServletContextListener {

	private final static Logger log = Logger.getLogger(Config.class);

	private SchedulerFactory sf;
	private Scheduler sched;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (event != null && event.getServletContext() != null) {
			if (sf != null) {
				try {

					log.debug("call quartz Scheduler.shutdown()");
					java.util.Collection<Scheduler> col = sf.getAllSchedulers();
					for (Scheduler s : col) {
						s.shutdown();
					}
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
			/*EntityManagerFactory emf = GenericDAOImpl.getEntityManagerFactory("postgresqlPU");
			emf.close();*/
			EntityManagerUtil.closeEntityManagerFactory("postgresPU");

			LogManager.shutdown();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			// Registra qual implementa��o da f�brica ser� usada
			Class.forName("br.com.motorapido.dao.impl.postgres.PostgresFabricaDAOImpl");
		} catch (ClassNotFoundException e) {
			log.error("Erro", e);
		}

		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");

		try {

			sf = new StdSchedulerFactory("quartz.properties");
			sched = sf.getScheduler();

			// Rotina padr�o
			JobDetail job = newJob(JobProject.class).withIdentity("JobProject", "GrupoMotoRapido").build();

			Trigger trigger = newTrigger().withIdentity("MotoRapidoTrigger", "GrupoMotoRapido").startNow()
					.withSchedule(simpleSchedule().withIntervalInHours(1).repeatForever()).build();
			sched.scheduleJob(job, trigger);
			sched.start();
		} catch (Exception ex) {
			log.error("Erro", ex);
		}

	}
}
