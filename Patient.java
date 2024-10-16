public class Patient {
    private String p_name;
    private int p_urgency;
    private long p_time_in;
    private int posInQueue;

    /*
     * constructor: creates a Patient with the given name*, urgency 
     * level, and time_in
     */
    public Patient(String name, int urgency, long time_in) {
        p_name = name;
        p_urgency = urgency;
        p_time_in = time_in;
        posInQueue = -1;
    }


    /*
     * returns Patient’s name
     */
    public String name() {
        return p_name;
    }

    /*
     * returns Patient’s urgency level
     */
    public int urgency() {
        return p_urgency;
    }

    /*
     * sets a Patient’s urgency level
     */
    public void setUrgency(int urgency) {
        p_urgency = urgency;
    }

    // returns the timeIn of the patient
    public long timeIn() {
        return p_time_in;
    }

    // sets the posInQueue of the patient
    public void setPosInQueue(int pos) {
        posInQueue = pos;
    }

    // rreturns the posInQueue of the Patient
    public int posInQueue() {
        return posInQueue;
    }


    /*
     * compares two Patients according to prioritization (positive result 
     * means higher priority relative to other); priority is based on (1) 
     * urgency level and (2) time in, meaning that a higher urgency level 
     * automatically gets priority; if the urgency levels are equal, an earlier t
     * ime in gets priority
     */
    public int compareTo(Patient other) {
        if (this.urgency() > other.urgency()) {
            return 1;
        }
        else if (this.urgency() < other.urgency()) {
            return -1;
        }
        else if (this.timeIn()> other.timeIn()) {
            return 1;
        }
        else if (this.timeIn() < other.timeIn()) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /*
     * returns a String containingPatient’s name, urgency level, time in, and 
     * position in the queue. These values should be separated by commas. For 
     * example: “Alice Jones, 10, 1000, 3”
     */
    public String toString() {
        String returnString = name();
        returnString += ", ";
        returnString += urgency();
        returnString += ", ";
        returnString += timeIn();
        returnString += ", ";
        returnString += posInQueue();
        return returnString;
    }
}