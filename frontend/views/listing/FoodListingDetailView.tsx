import React, {useEffect, useMemo, useState} from 'react';
import Gallery from './Gallery';
import FoodListing from "Frontend/generated/cz/asen/fridge/domain/FoodListing";
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import {NavLink, useParams} from "react-router-dom";
import {Tabs} from "@hilla/react-components/Tabs";
import {Tab} from "@hilla/react-components/Tab";
import {Icon} from "@hilla/react-components/Icon";
import {ConfirmDialog} from "@hilla/react-components/ConfirmDialog";

const iconStyle= "h-[var(--lumo-icon-size-s)] m-auto w-[var(--lumo-icon-size-s)]"

export default function FoodListingDetailView() {
    const { listingId } = useParams()
    const [confirmDialogOpened, setConfirmDialogOpened] = useState(false);
    const [listing, setListing] = useState<FoodListing>()

    useEffect(() => {
        FoodListingEndpoint.getListing(listingId as unknown as number).then((result) => setListing(result))
    }, []);

    return (
        <>
            <ConfirmDialog
                header='Delete?'
                cancelButtonVisible
                confirmText="Delete"
                confirmTheme="error primary"
                opened={confirmDialogOpened}
                onOpenedChanged={(event) => setConfirmDialogOpened(event.detail.value)}
                onConfirm={() => {
                    console.log("Order Cancelled")
                }}
                onReject={() => {
                    console.log("Discard")
                }}
            >
                By cancelling you won't be able to do stuff.
            </ConfirmDialog>

            <div className="p-4 mx-auto max-w-4xl">
                <div className="flex justify-between items-start border-b pb-3 mb-3">
                    <h1 className="text-2xl overflow-wrap">{listing?.shortDescription}</h1>
                    <div className="text-xs text-gray-500">{new Date(listing?.created as string).toDateString()}</div>
                </div>

                <Gallery images={listing?.base64Images}/>

                <div className="py-2">
                    <h2 className="font-bold mb-2">Donor Information:</h2>
                    <p>Name: {listing?.donor?.name}</p>
                    <p>Email: <a href={`mailto:${listing?.donor?.email}`}>{listing?.donor?.email}</a></p>
                    <p>Mobile: {listing?.donor?.phone}</p>
                </div>

                <div className="py-2">
                    <h2 className="font-bold mb-2">Expires On:</h2>
                    <p>{new Date(listing?.expiryDate as string).toDateString()}</p>
                </div>

                <div className="py-2">
                    <h2 className="font-bold mb-2">Pickup Location:</h2>
                    <p>{listing?.pickupLocation}</p>
                </div>

                <div className="py-2">
                    <h2 className="font-bold mb-2">Dietary Properties:</h2>
                    <p>{listing?.allergens != null ? listing?.allergens?.join(", ") : "NONE"}</p>
                </div>

                <div className="py-2">
                    <h2 className="font-bold mb-2">Description:</h2>
                    <p>{listing?.description}</p>
                </div>
            </div>

            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs" className="w-full bottom-0 lg:flex lg:justify-center lg:gap-6">
                <Tab aria-label="Back">
                    <NavLink to="/food-listings" tabIndex={-1}>
                        <Icon icon="vaadin:arrow-circle-left" className={iconStyle}/>
                    </NavLink>
                </Tab>

                <Tab aria-label="Claim">
                    <NavLink to="#" onClick={() => alert("Order claimed")} tabIndex={-1}>
                        <Icon icon="vaadin:location-arrow-circle" className={iconStyle}/>
                    </NavLink>
                </Tab>

                <Tab aria-label="Edit">
                    <NavLink to="#" onClick={() => alert("Editing listing")} tabIndex={-1}>
                        <Icon icon="vaadin:ellipsis-circle" className={iconStyle}/>
                    </NavLink>
                </Tab>

                <Tab aria-label="Cancel">
                    <NavLink to="#" onClick={() => setConfirmDialogOpened(true)} tabIndex={-1}>
                        <Icon icon="vaadin:close-circle" className={iconStyle}/>
                    </NavLink>
                </Tab>
            </Tabs>
        </>
    );
};