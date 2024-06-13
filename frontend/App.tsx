import {router} from "Frontend/routes.js";
import "./main.css"
import {RouterProvider} from "react-router-dom";
import {UserProvider} from "Frontend/components/UserProvider";

export default function App() {
  return(
      <UserProvider>
        <RouterProvider router={router} />
      </UserProvider>
  );
}
