package ro.teamnet.zth.app.dao;

import java.util.List;
import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.app.domain.Job;

public class JobDao {

    EntityManager entityManager = new EntityManagerImpl();

    /**
     *
     * @param job
     * @return job object
     */
    public Job insertJob(Job job) {
        return (Job) entityManager.insert(job);
    }

    /**
     *
     * @param job
     * @return job object
     */
    public Job updateJob(Job job) {
        return entityManager.update(job);
    }

    /**
     *
     * @param job
     */
    public void deleteJob(Job job) {
        entityManager.delete(job);
    }

    /**
     *
     * @return a list of jobs
     */
    public List<Job> getAllJobs() {
        EntityManager entityManager = new EntityManagerImpl();
        return entityManager.findAll(Job.class);
    }

    /**
     *
     * @param id
     * @return job object
     */
    public Job getJobById(Integer id) {
        return entityManager.findById(Job.class, id);
    }

}
