import { AppLayout, type AppLayoutElement } from '@hilla/react-components/AppLayout.js';
import React, {useEffect, useRef, useState} from 'react';
import {Tab} from "@hilla/react-components/Tab";
import {Tabs} from "@hilla/react-components/Tabs";
import {Icon} from "@hilla/react-components/Icon";
import {HorizontalLayout} from "@hilla/react-components/HorizontalLayout";
import {NavLink, Outlet, Route, useLocation, useNavigate} from "react-router-dom";
import {ConfirmDialog} from "@hilla/react-components/ConfirmDialog";
import {Button} from "@hilla/react-components/Button";
import {MenuBar} from "@hilla/react-components/MenuBar";
import {createRoot} from "react-dom/client";

const iconStyle = {
    height: 'var(--lumo-icon-size-s)',
    margin: 'auto',
    width: 'var(--lumo-icon-size-s)',
};

const tabsStyle = {
    width: '100%',
    bottom: 0
};

const MenuContext = React.createContext(undefined);

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

    // Header
    function menuComponent(component: React.ReactNode) {
        const container = document.createElement('vaadin-menu-bar-item');
        createRoot(container).render(component);
        return container;
    }

    function createItem(iconName: string, text: string, isChild = false) {
        const iconStyle: React.CSSProperties = {};
        if (isChild) {
            iconStyle.width = 'var(--lumo-icon-size-s)';
            iconStyle.height = 'var(--lumo-icon-size-s)';
            iconStyle.marginRight = 'var(--lumo-space-s)';
        }

        let ariaLabel = '';
        if (iconName === 'copy') {
            ariaLabel = 'duplicate';
        }

        return menuComponent(
            <>
                <Icon icon={`vaadin:${iconName}`} style={iconStyle} aria-label={ariaLabel} />
                {text}
            </>
        );
    }

    const menuItems = [
        {
            component: createItem('menu', ''),
            children: [
                { component: createItem('user', 'Account', true) },
                { component: createItem('notebook', 'My Claims', true) },
                { component: 'hr' },
                { component: createItem('sign-out', 'Logout', true) },
            ],
        }
    ];

    return (
        <AppLayout ref={appLayoutRef}>
            <header slot="navbar" className="bg-gray-800 p-4 text-white w-full">
                <div className="flex items-center justify-between">
                    <MenuBar items={menuItems} onItemSelected={(event) => alert(JSON.stringify(event.detail.value))}/> Food Rescue Fridge App
                </div>
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
                {location.pathname !== "/food-listings" && (
                    <Tab aria-label="Back">
                        <NavLink to="/food-listings" tabIndex={-1}>
                            <Icon icon="vaadin:arrow-circle-left" style={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                {location.pathname === "/food-listings" && (
                    <Tab aria-label="Create">
                        <NavLink to="/add-listing" tabIndex={-1}>
                            <Icon icon="vaadin:plus-circle" style={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                {location.pathname === "/food-listings/" && (
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

