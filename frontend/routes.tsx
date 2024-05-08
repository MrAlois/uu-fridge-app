import {
    createBrowserRouter, Navigate,
    RouteObject
} from "react-router-dom";
import React from "react";
import ListingDetailView from "Frontend/views/listing/ListingDetailView";
import CreateListingView from "Frontend/views/listing/CreateListingView";
import UserDetailView from "Frontend/views/user/UserDetailView";
import MainLayout from "Frontend/views/MainLayout";
import LoginView from "Frontend/views/login/LoginView";
import ListingView from "Frontend/views/listing/ListingView";

export const routes: readonly RouteObject[] = [
    {
        element: <MainLayout />,
        children: [
            { path: "/", element: <Navigate to="/food-listings" /> },
            { path: "/food-listings", element: <ListingView /> },
            { path: "/food-listings/:id", element: <ListingDetailView /> },
            { path: "/add-listing", element: <CreateListingView />},
            { path: "/profile", element: <UserDetailView />},
            { path: "/login", element: <LoginView />}
        ]
    }
];

export const router = createBrowserRouter([...routes], {basename: new URL(document.baseURI).pathname });