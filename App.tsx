import { StatusBar } from "expo-status-bar";
import { Alert, Button, StyleSheet, Text, TextInput, View } from "react-native";
import * as FileSystem from "expo-file-system";
import { useState } from "react";

const FILE_PATH = FileSystem.documentDirectory + "phonenumber.txt";

export default function App() {
  const [fileContent, setFileContent] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  const writeToFile = async () => {
    FileSystem.writeAsStringAsync(
      FileSystem.documentDirectory + "test.txt",
      "Hello, world!",
      { encoding: FileSystem.EncodingType.UTF8 }
    );
    console.log("Path to file: ", FileSystem.documentDirectory + "test.txt");
    console.log("File written!");
  };

  const readDataFromFile = async () => {
    const data = await FileSystem.readAsStringAsync(
      FileSystem.documentDirectory + "test.txt",
      { encoding: FileSystem.EncodingType.UTF8 }
    );
    console.log("Data read from file: ", data);
    setFileContent(data);
  };

  const savePhoneNumber = async () => {
    // Validate phone number and save it to file
    if (!phoneNumber) {
      Alert.alert("Please enter a phone number");
      return;
    }
    if (!/^\d{10}$/.test(phoneNumber)) {
      Alert.alert("Please enter a valid phone number");
      return;
    }

    await FileSystem.writeAsStringAsync(FILE_PATH, phoneNumber, {
      encoding: FileSystem.EncodingType.UTF8,
    });
    Alert.alert("Phone number saved!");
  };

  const readPhoneNumber = async () => {
    const data = await FileSystem.readAsStringAsync(FILE_PATH, {
      encoding: FileSystem.EncodingType.UTF8,
    });
    setPhoneNumber(data);
    Alert.alert("Phone number read!");
  };

  return (
    <View style={styles.container}>
      <Text>Open up App.tsx to start working on your app!</Text>
      <Text style={{ fontSize: 32 }}>Hello world</Text>

      <View>
        <TextInput
          value={phoneNumber}
          onChangeText={setPhoneNumber}
          placeholder="Enter Phone number"
          style={{
            borderWidth: 1,
            borderColor: "black",
            padding: 10,
            margin: 10,
            width: 200,
            borderRadius: 5,
          }}
          keyboardType="phone-pad"
        />
        <Button title="Save Phone number" onPress={savePhoneNumber} />
      </View>

      <View
        style={{
          margin: 10,
        }}
      >
        <Button title="Read Phone number" onPress={readPhoneNumber} />
        <Text>Phone number: {phoneNumber}</Text>
      </View>

      <View
        style={{
          margin: 10,
        }}
      >
        <Button title="Write to file" onPress={writeToFile} />
        <Button title="Read from file" onPress={readDataFromFile} />
      </View>

      <Text
        style={{
          fontSize: 16,
          margin: 10,
          padding: 10,
          backgroundColor: "lightgray",
        }}
      >
        File Content: {fileContent}
      </Text>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
