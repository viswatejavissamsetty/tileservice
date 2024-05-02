import * as Location from "expo-location";
import { useEffect } from "react";
import { Alert } from "react-native";

export function LocationPermission() {
  useEffect(() => {
    (async () => {
      const { status } = await Location.requestBackgroundPermissionsAsync();
      if (status !== "granted") {
        Alert.alert("Permission to access location was denied");
      }
    })();
  }, []);

  return null;
}
