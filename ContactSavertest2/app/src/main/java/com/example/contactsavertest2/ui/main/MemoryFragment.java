package com.example.contactsavertest2.ui.main;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;
import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contactsavertest2.MainActivity;
import com.example.contactsavertest2.R;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MemoryFragment extends Fragment {

    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    ArrayList<String> pathHistory;
    String lastDirectory;
    Button btnBack,btnHome;
    int count = 0;
    ListView Storage;

    private List<Integer> transferList;

    public static MemoryFragment newInstance() {
        return new MemoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);
        getInformation(view);
        return view;
    }

    private void getInformation(View view) {
        MainActivity main = (MainActivity) getActivity();
        transferList = main.getTransferList();
        Storage = (ListView) view.findViewById(R.id.lvInternalStorage);
        btnBack = (Button) view.findViewById(R.id.btnUpDirectory);
        btnHome = (Button) view.findViewById(R.id.btnHomeDirectory);

        Storage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if (lastDirectory.equals(adapterView.getItemAtPosition(i))) {


                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);

                } else {
                    count++;
                    pathHistory.add(count, (String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();

                }
            }
        });

        //Goes up one directory level
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                } else {
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });


        //Opens the SDCard or phone memory
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count, System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });
    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {

            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];
            Log.d("Files", "FileName:" + listFile.length);
            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, FilePathStrings);
            Storage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }
    private void readExcelData(String filePath)
    {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try
        {

            int fnameColumn= transferList.get(0);
            int lnameColumn = transferList.get(1);
            int DegreeColumn = transferList.get(2);
            int CountryColumn = transferList.get(3);
            int phNumberColumn = transferList.get(4);
            int semester = transferList.get(5);
            int year = transferList.get(6)%100;
            int Rtype = transferList.get(7);
            String texts;
            if(semester == 0)

                texts ="S";

            else
                texts="W";

            String yearS = Integer.toString(year);

            InputStream inputStream = new FileInputStream(inputFile);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outter loop, loops through rows
            for (int r = 1; r <= rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                Cell cell = row.getCell(fnameColumn);

                String name = cell.getStringCellValue();
                cell = row.getCell(lnameColumn);
                String name2 = cell.getStringCellValue();
                cell = row.getCell(DegreeColumn);
                String degree =cell.getStringCellValue();
                cell = row.getCell(CountryColumn);
                String countryname = cell.getStringCellValue();

                cell = row.getCell(phNumberColumn);
                String num =cell.getStringCellValue();
                String deg = getDegreecode(degree);
                String contactname;
                if(Rtype==0)
                {
                     contactname = name + " " + name2 + "_" + deg + "_" + countryname + "_" + texts + yearS;
                }
                else
                {
                     contactname = name + " " + name2 + "_" +    countryname + "_" +deg+ "_"+ texts + yearS;
                }

                buttonAddContact(contactname,num);
                Log.d(TAG, "readExcelData: : " + name+degree);

            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());



        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }

    public void buttonAddContact(String name, String num){

        ArrayList<ContentProviderOperation> contentProviderOperations
                = new ArrayList<ContentProviderOperation>();

        contentProviderOperations.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // Adding Name
        contentProviderOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        name)
                .build());

        // Adding Number
        contentProviderOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        num)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build());

        try {
            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private String getDegreecode(String degree)
    {
        String degree2=degree.toLowerCase();
        if(degree2.contains("computer science"))
            return "CS";
        if(degree2.contains("computer eng"))
            return "CE";
        if(degree2.contains("civil"))
            return "CAE";
        if(degree2.contains("electrical"))
            return "EE";
        if(degree2.contains("mechanical"))
            return "ME";
        if(degree2.contains("mathematics") && degree2.contains("econom"))
            return "MATH-ECON";
        if(degree2.contains("mathematics"))
            return "MATH";
        if(degree2.contains("statistics"))
            return "STAT";
        if(degree2.contains("biotechnology") && degree2.contains("chemical"))
            return "BCE";
        if(degree2.contains("technology based business"))
            return "TBBD";
        else {
            String arr[] = degree.split(" ", 2);
            return arr[0];
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.getContext().checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.getContext().checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

}