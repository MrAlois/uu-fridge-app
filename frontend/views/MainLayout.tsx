import { AppLayout, type AppLayoutElement } from '@hilla/react-components/AppLayout.js';
import React, {useEffect, useRef, useState} from 'react';
import {Tab} from "@hilla/react-components/Tab";
import {Tabs} from "@hilla/react-components/Tabs";
import {Icon} from "@hilla/react-components/Icon";
import {HorizontalLayout} from "@hilla/react-components/HorizontalLayout";
import {NavLink, Outlet, Route, useLocation} from "react-router-dom";
import {ConfirmDialog} from "@hilla/react-components/ConfirmDialog";
import FoodListingCreateView from "Frontend/views/listing/FoodListingCreateView";


const headerStyle = {
    fontSize: 'var(--lumo-font-size-l)',
    margin: 'var(--lumo-space-m) var(--lumo-space-l)',
    width: "100%"
};

const iconStyle = {
    height: 'var(--lumo-icon-size-s)',
    margin: 'auto',
    width: 'var(--lumo-icon-size-s)',
};

const tabsStyle = {
    width: '100%',
    bottom: 0
};

export default function MainLayout() {
    const appLayoutRef = useRef<AppLayoutElement>(null);
    const location = useLocation();

    const [dialogOpened, setDialogOpened] = useState(false);

    useEffect(() => {
        const appLayout = appLayoutRef.current;
        if (appLayout) {
            // --vaadin-app-layout-touch-optimized is only enforced as part of this example
            appLayout.style.setProperty('--vaadin-app-layout-touch-optimized', 'true');
            (appLayout as any)._updateTouchOptimizedMode();
        }
    }, []);

    return (
        <AppLayout ref={appLayoutRef}>
            <header slot="navbar" style={headerStyle}>
                <HorizontalLayout>
                    Food Rescue Fridge App
                </HorizontalLayout>
            </header>

            <ConfirmDialog
                header='Delete?'
                cancelButtonVisible
                confirmText="Delete"
                confirmTheme="error primary"
                opened={dialogOpened}
                onOpenedChanged={(event) => setDialogOpened(event.detail.value)}
                onConfirm={() => {
                    console.log("Order Cancelled")
                }}
                onReject={() => {
                    console.log("Discared")
                }}
            >
                By cancelling you won't be able to do stuff.
            </ConfirmDialog>

            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs" style={tabsStyle}>
                { location.pathname !== "/food-listings" && (
                    <Tab aria-label="Back">
                        <NavLink to="/food-listings" tabIndex={-1}>
                            <Icon icon="vaadin:arrow-circle-left" style={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                { location.pathname === "/food-listings" && (
                    <Tab aria-label="Create">
                        <NavLink to="/add-listing" tabIndex={-1}>
                            <Icon icon="vaadin:plus-circle" style={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                { location.pathname === "/food-listings/" && (
                    <Tab aria-label="Cancel">
                        <NavLink to="#" onClick={() => setDialogOpened(true)} tabIndex={-1}>
                            <Icon icon="vaadin:close-circle" style={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}
            </Tabs>

            <Outlet/>
        </AppLayout>
    );
};

