import { CapacitorConfig } from '@capacitor/cli';
import {credentials} from "./src/environments/credentials-googleAuth";

const config: CapacitorConfig = {
  appId: 'io.ionic.starter',
  appName: 'adjudi-cat-FE',
  webDir: 'www',
  server: {
    androidScheme: 'https'
  },
  plugins: {
    GoogleAuth: {
      scopes: ['email', 'profile'],
      serverClientId: credentials.clientId
    }
  }
};

export default config;
