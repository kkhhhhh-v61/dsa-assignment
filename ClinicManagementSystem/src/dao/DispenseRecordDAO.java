package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.DispenseRecord;
import utility.DataManager;
import java.io.Serializable;
/**
 *
 * @author Lai Chee Kit
 */
public class DispenseRecordDAO implements Serializable {
    private ListInterface<DispenseRecord> dispenseRecordList;
    private static final String FILE_PATH = "data/dispenseRecords.dat";

    public DispenseRecordDAO() {
        dispenseRecordList = loadDispenseRecords();
        if (dispenseRecordList == null) {
            dispenseRecordList = new ArrayList<>();
            saveDispenseRecords();
        } else {
            int maxId = 0;
            for (int i = 0; i < dispenseRecordList.size(); i++) {
                DispenseRecord record = dispenseRecordList.get(i + 1);
                String idStr = record.getDispenseId().substring(1); // Remove 'D' prefix
                try {
                    int id = Integer.parseInt(idStr);
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing dispense ID: " + record.getDispenseId());
                }
            }
            DispenseRecord.setLastId(maxId);
        }
    }

    public ListInterface<DispenseRecord> getAllDispenseRecords() {
        return dispenseRecordList;
    }

    public DispenseRecord getDispenseRecordById(String dispenseId) {
        for (int i = 0; i < dispenseRecordList.size(); i++) {
            DispenseRecord record = dispenseRecordList.get(i + 1);
            if (record.getDispenseId().equalsIgnoreCase(dispenseId)) {
                return record;
            }
        }
        return null;
    }

    public void addDispenseRecord(DispenseRecord record) {
        dispenseRecordList.add(record);
        saveDispenseRecords();
    }

    public void updateDispenseRecord(DispenseRecord updatedRecord) {
        for (int i = 0; i < dispenseRecordList.size(); i++) {
            DispenseRecord record = dispenseRecordList.get(i + 1);
            if (record.getDispenseId().equalsIgnoreCase(updatedRecord.getDispenseId())) {
                dispenseRecordList.replace(i + 1, updatedRecord);
                saveDispenseRecords();
                return;
            }
        }
    }

    public void deleteDispenseRecord(String dispenseId) {
        for (int i = 0; i < dispenseRecordList.size(); i++) {
            DispenseRecord record = dispenseRecordList.get(i + 1);
            if (record.getDispenseId().equalsIgnoreCase(dispenseId)) {
                dispenseRecordList.remove(i + 1);
                saveDispenseRecords();
                return;
            }
        }
    }
    
    public ListInterface<DispenseRecord> getDispenseRecordsByTreatmentId(String treatmentId) {
        ListInterface<DispenseRecord> records = new ArrayList<>();
        for (int i = 0; i < dispenseRecordList.size(); i++) {
            DispenseRecord record = dispenseRecordList.get(i + 1);
            if (record.getTreatmentId().equalsIgnoreCase(treatmentId)) {
                records.add(record);
            }
        }
        return records;
    }

    private void saveDispenseRecords() {
        DataManager.saveToFile((ArrayList<DispenseRecord>) dispenseRecordList, FILE_PATH);
    }

    @SuppressWarnings("unchecked")
    private ListInterface<DispenseRecord> loadDispenseRecords() {
        return (ListInterface<DispenseRecord>) DataManager.readFromFile(FILE_PATH);
    }
    
    public int getLastId() {
        int maxId = 0;
        for (int i = 1; i <= dispenseRecordList.size(); i++) {
            String id = dispenseRecordList.get(i).getDispenseId();
            try {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            } catch (NumberFormatException e) {
                // ignore malformed ID
            }
        }
        return maxId;
    }
    
    public void reload() {
        dispenseRecordList = loadDispenseRecords();
    }
}
