import {Navigate} from "react-router-dom";
import {AuthContext} from "@hilla/react-auth";
import React, {useContext} from "react";

function AuthRoute({ children }: Readonly<{ children: JSX.Element }>) {
    const { state } = useContext(AuthContext); // Access isLoggedIn state

    if (!state.initializing) {
        return <Navigate to="/login" replace />;
    }

    return children;
}