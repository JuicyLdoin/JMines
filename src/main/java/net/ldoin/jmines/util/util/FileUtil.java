package net.ldoin.jmines.util.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import net.ldoin.jmines.util.gson.LocationAdapter;
import org.bukkit.Location;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings({"unused"})
public class FileUtil {

    private GsonBuilder getGsonBuilder() {

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Location.class, new LocationAdapter());

    }

    public String readStringInJson(File file, String field) {

        try {

            Gson gson = getGsonBuilder().create();
            JsonReader jsonReader = new JsonReader(new FileReader(file));
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);

            return jsonObject.get(field).getAsString();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public <T> T readJson(File file, Type type, Map<Class<?>, TypeAdapter<?>> typeAdapters) {

        try {

            GsonBuilder gsonBuilder = getGsonBuilder();
            typeAdapters.forEach(gsonBuilder::registerTypeAdapter);

            return gsonBuilder.create().fromJson(new FileReader(file), type);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public <T> T readJson(File file, Class<T> clazz, Map<Class<?>, TypeAdapter<?>> typeAdapters) {

        try {

            GsonBuilder gsonBuilder = getGsonBuilder();
            typeAdapters.forEach(gsonBuilder::registerTypeAdapter);

            return gsonBuilder.create().fromJson(new FileReader(file), clazz);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public <T> T readJson(File file, Class<T> clazz) {

        return readJson(file, clazz, new HashMap<>());

    }

    public <T> T readJson(File file, Type type) {

        return readJson(file, type, new HashMap<>());

    }

    public void writeJson(File file, Object object, Map<Class<?>, TypeAdapter<?>> typeAdapters) {

        try {

            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);

            GsonBuilder gsonBuilder = getGsonBuilder();
            typeAdapters.forEach(gsonBuilder::registerTypeAdapter);

            gsonBuilder.create().toJson(object, fileWriter);

            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public void writeJson(File file, Object object) {

        writeJson(file, object, new HashMap<>());

    }

    public void writeFile(Path path, byte[] bytes) throws IOException {

        File parent = path.getParent().toFile();

        if (!parent.exists())
            parent.mkdirs();

        File file = path.toFile();

        if (!file.exists())
            file.createNewFile();

        Files.write(path, bytes);

    }

    public byte[] toByteArray(File file) throws IOException {

        return Files.readAllBytes(file.toPath());

    }

    public long fileLength(File file) throws IOException {

        long length = 0;

        if (file.isDirectory())
            for (File files : file.listFiles())
                length += fileLength(files);
        else
            length = toByteArray(file).length;

        return length;

    }

    public ImmutableList<File> getFilesInFolder(File folder) {

        if (!folder.exists())
            return ImmutableList.copyOf(Collections.emptyList());

        if (!folder.isDirectory())
            return ImmutableList.copyOf(Collections.emptyList());

        List<File> files = new ArrayList<>();

        for (File fileList : Objects.requireNonNull(folder.listFiles())) {

            if (fileList.isDirectory()) {

                files.addAll(getFilesInFolder(fileList));
                continue;

            }

            files.add(fileList);

        }

        return ImmutableList.copyOf(files);

    }

    public void createFile(File file) throws IOException {

        if (file.exists())
            return;

        File parent = file.getParentFile();

        if (!parent.exists()) {

            createFile(parent);
            parent.mkdirs();

        }

        if (!file.getName().contains("."))
            file.mkdirs();
        else
            file.createNewFile();

    }

    public boolean deleteDirectory(File directoryToBeDeleted) {

        File[] allContents = directoryToBeDeleted.listFiles();

        if (allContents != null)
            for (File file : allContents)
                deleteDirectory(file);

        return directoryToBeDeleted.delete();

    }

    public void copy(File source, File destination, List<String> ignore) throws IOException {

        if (source.isDirectory())
            copyDirectory(source, destination, ignore);
        else
            copyFile(source, destination);

    }

    public void copy(File source, File destination) throws IOException {

        copy(source, destination, new ArrayList<>());

    }

    public void copy(URL source, File destination, List<String> ignore) throws IOException, URISyntaxException {

        copy(new File(source.toURI()), destination, ignore);

    }

    public void copy(URL source, File destination) throws IOException, URISyntaxException {

        copy(source, destination, new ArrayList<>());

    }

    public void copy(InputStream in, File destination) throws IOException {

        copyFile(in, destination);

    }

    private void copyDirectory(File sourceDirectory, File destinationDirectory, List<String> ignore) throws IOException, NullPointerException {

        if (!destinationDirectory.exists())
            destinationDirectory.mkdir();

        for (String file : Objects.requireNonNull(sourceDirectory.list()))
            if (!ignore.contains(file))
                copy(new File(sourceDirectory, file), new File(destinationDirectory, file));

    }

    private void copyFile(InputStream in, File destinationFile) throws IOException {

        try (OutputStream out = Files.newOutputStream(destinationFile.toPath())) {

            byte[] buf = new byte[1024];
            int length;

            while ((length = in.read(buf)) > 0)
                out.write(buf, 0, length);

        }
    }

    private void copyFile(File sourceFile, File destinationFile) throws IOException {

        try (InputStream in = Files.newInputStream(sourceFile.toPath()); OutputStream out = Files.newOutputStream(destinationFile.toPath())) {

            byte[] buf = new byte[1024];
            int length;

            while ((length = in.read(buf)) > 0)
                out.write(buf, 0, length);

        }
    }
}