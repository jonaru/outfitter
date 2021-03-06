package com.example.jonatan.clothesplanner.wardrobe.filehandling;

import android.content.Context;

import com.example.jonatan.clothesplanner.R;
import com.example.jonatan.clothesplanner.WardrobeException;
import com.example.jonatan.clothesplanner.wardrobe.IStorageAdapter;
import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.wardrobeitem.IWardrobeItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Jonatan on 2017-01-28.
 */

public class FileHandlingHelper implements IFileHandlingHelper, IStorageAdapter {

    private final Context myContext;

    public FileHandlingHelper(Context context)
    {
        myContext = context;
    }

    @Override
    public void loadWardrobe(IWardrobe wardrobe) {
        try {
            readWardrobeFromFile(myContext.getResources().getString(R.string.saved_shirts), myContext.getResources().getString(R.string.shirt), wardrobe);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WardrobeException e) {
            e.printStackTrace();
        }

        try {
            readWardrobeFromFile(myContext.getResources().getString(R.string.saved_trousers), myContext.getResources().getString(R.string.trousers), wardrobe);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WardrobeException e) {
            e.printStackTrace();
        }
    }

    private void readWardrobeFromFile(String fileString, String itemTypeString, IWardrobe wardrobe) throws IOException, WardrobeException {
        FileInputStream fileInputStream = myContext.openFileInput(fileString);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            wardrobe.addWardrobeItem(trimmedLine, itemTypeString);
        }

        reader.close();
    }

    @Override
    public void storeWardrobe(IWardrobe wardrobe) {
        try {
            storeToFile(wardrobe.getUpperItems(), myContext.getResources().getString(R.string.saved_shirts));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            storeToFile(wardrobe.getLowerItems(), myContext.getResources().getString(R.string.saved_trousers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[] loadWeeklyPlanIndex() {
        FileInputStream fileInputStream = null;
        int indices[] = new int[2];
        indices[0] = 0;
        indices[1] = 0;

        try {
            fileInputStream = myContext.openFileInput(myContext.getResources().getString(R.string.weekly_plan));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String currentLine;
            if((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                indices[0] = Integer.parseInt(trimmedLine);
            }
            if((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                indices[1] = Integer.parseInt(trimmedLine);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return indices;
    }

    @Override
    public void storeWeeklyPlanIndex(int weeklyPlanShirtIndex, int weeklyPlanTrousersIndex) {
        myContext.deleteFile(myContext.getResources().getString(R.string.weekly_plan));
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = myContext.openFileOutput(myContext.getResources().getString(R.string.weekly_plan), Context.MODE_PRIVATE);
            fileOutputStream.write(Integer.toString(weeklyPlanShirtIndex).getBytes());
            fileOutputStream.write(System.getProperty("line.separator").getBytes());
            fileOutputStream.write(Integer.toString(weeklyPlanTrousersIndex).getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeStorage() {
        //nothing to be done
    }

    private void storeToFile(List<IWardrobeItem> wardrobeItemList, String fileString) throws IOException {
        myContext.deleteFile(fileString);
        FileOutputStream fileOutputStream = myContext.openFileOutput(fileString, Context.MODE_PRIVATE);

        for (IWardrobeItem item : wardrobeItemList)
        {
            fileOutputStream.write(item.getWardrobeItemString().getBytes());
            fileOutputStream.write(System.getProperty("line.separator").getBytes());
        }

        fileOutputStream.close();
    }

    /*
    private boolean removeWardrobeItemFromFile(String lineToRemove, String wardrobeItemFile) throws IOException {
        FileInputStream fileInputStream = myActivity.openFileInput(wardrobeItemFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        final String myTempFileString = "myTempFile.txt";
        FileOutputStream fileOutputStream = myActivity.openFileOutput(myTempFileString, Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

        File inputFile = new File(myActivity.getFilesDir()+"/"+wardrobeItemFile);
        File tempFile = new File(myActivity.getFilesDir()+"/"+myTempFileString);
        return tempFile.renameTo(inputFile);
    }
    */
}
