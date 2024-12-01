public class Clinic {
    private int threshold;
    private int N;
    private int erTotal; // Number of patients sent to the ER
    private int walkOuts; // Number of patients that walked out before seeing the doc
    private PatientQueue queue; // Heap data structure used to efficiently access Patient objects
    private int processed; // number of patients that have been processed
    private int seenByDoc; // numbers of patients seen by the doctor


    /*
     *Constructor: Create a new Clinic instance. The er_threshold indicates the highest level of 
     urgency the clinic can handle. If a patient’s urgency level exceeds that, they are sent to 
     the ER instead.
     */
    public Clinic(int er_threshold) {
        threshold = er_threshold;
        N = 0;
        erTotal = 0;
        walkOuts = 0;
        processed = 0;
        queue = new PatientQueue();
        seenByDoc = 0;
    }

    /*
     * Return the ER threshold
     */
    public int er_threshold() {
        return threshold;
    }

    /*
     * Process the new patient with the given name and urgency. If their urgency level exceeds 
     * er_threshold, send them to the ER. If not, add them to the patient queue. Return the name 
     * of the patient.
     */
    public String process(String name, int urgency) {
        Patient p = new Patient(name, urgency,(long) 0);
        processed++;
        if(p.urgency()>threshold) {
            erTotal++;
            return null;
        }
        else {
            queue.insert(p);
            N++;
        }

        return name;
    }

    /*
     * Send the next patient in the queue to be seen by a doctor.
     */
    public String seeNext() {
        try {
            Patient p = queue.removeNext();
            seenByDoc++;
            return p.name();
        }
        catch(EmptyQueueException E) {
            return null;
        }
        
    }

    /*
     * A patient with the given name experiences an emergency while waiting. Update their urgency level. 
     * If it exceeds the er_threshold, send them directly to the ER (and remove them from the queue). 
     * Otherwise, update their urgency level. Return true if the Patient was sent to the ER and false 
     * otherwise.
     */
    public boolean handle_emergency(String name, int urgency) {
        if (urgency > threshold) {
            erTotal++;
            queue.remove(name);
            N--;
            return true;
        }
        queue.update(name, urgency);
        return false;
    }

    /*
     * A patient with the given name walks out before being seen by a doctor. Remove them from the queue.
     */
    public void walk_out(String name) {
        queue.remove(name);
        N--;
        walkOuts++;
    }

    /*
     * Return the number of patients that have been processed.
     */
    public int processed() {
        return processed;
    }

    /*
     * Return the number of patients that have been sent to the ER.
     */
    public int sentToER() {
        return erTotal;
    }

    /*
     * Return the number of patients that have been seen by a doctor 
     * at the clinic.
     */
    public int seenByDoctor() {
        return seenByDoc;
    }

    /*
     * Return the number of patients that walked out before being
     * seen by a doctor.
     */
    public int walkedOut() {
        return walkOuts;
    }
}
