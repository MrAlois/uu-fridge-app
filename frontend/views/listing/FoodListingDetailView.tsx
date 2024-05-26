import React, {useEffect, useState} from 'react';
import Gallery from './Gallery';
import FoodListing from "Frontend/generated/cz/asen/unicorn/fridge/domain/FoodListing";
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import {NavLink, useNavigate, useParams} from "react-router-dom";
import {Tabs} from "@hilla/react-components/Tabs";
import {Tab} from "@hilla/react-components/Tab";
import {Icon} from "@hilla/react-components/Icon";
import {ConfirmDialog} from "@hilla/react-components/ConfirmDialog";
import StateBadge from "Frontend/views/listing/StateBadge";
import ClaimState from "Frontend/generated/cz/asen/unicorn/fridge/domain/enums/ClaimState";

const iconStyle= "h-[var(--lumo-icon-size-s)] m-auto w-[var(--lumo-icon-size-s)]"

export default function FoodListingDetailView() {
    const { listingId } = useParams()
    const navigate = useNavigate()
    const [removeDialogOpened, setRemoveDialogOpened] = useState(false);
    const [unclaimDialogOpened, setUnclaimDialogOpened] = useState(false);
    const [listing, setListing] = useState<FoodListing>()

    const isCurrentUserOwner = false                     //TODO Auth system
    const isCurrentUserClaiming = !isCurrentUserOwner   //TODO Auth system

    useEffect(() => {
        FoodListingEndpoint.getListing(listingId as unknown as number).then((result) => setListing(result))
    }, []);

    return (
        <>
            <ConfirmDialog
                header='Remove listing'
                cancelButtonVisible
                confirmText="Delete"
                confirmTheme="error primary"
                opened={removeDialogOpened}
                onOpenedChanged={(event) => setRemoveDialogOpened(event.detail.value)}
                onConfirm={() =>
                    FoodListingEndpoint.removeListing(Number(listingId)).then(r => navigate("/food-listings"))
                }
            >
                <p>Are you sure you want to remove this listing?</p>
            </ConfirmDialog>

            <ConfirmDialog
                header='Unclaim listing'
                cancelButtonVisible
                confirmText="Unclaim"
                confirmTheme="error primary"
                opened={unclaimDialogOpened}
                onOpenedChanged={(event) => setUnclaimDialogOpened(event.detail.value)}
                onConfirm={() => console.log("Order unclaimed")}
            >
                <p>If you're cancelling your claim, you should contact your donor. Are you sure?</p>
                <p className="my-4"><span>Email: </span> <a href={`mailto:${listing?.donor?.email}`}>{listing?.donor?.email}</a></p>
            </ConfirmDialog>

            <div className="p-4 mx-auto max-w-4xl">
                <div className="flex justify-between items-start border-b pb-3 mb-3">
                    <h1 className="text-2xl overflow-wrap">{listing?.shortDescription}</h1>
                    <div className="text-xs text-gray-500">{new Date(listing?.created as string).toDateString()}</div>
                </div>

                <Gallery images={listing?.base64Images}/>

                <section className="my-10">
                    <span className="text-2xl leading-none tracking-tight text-gray-900 md:text-5x">Listing information</span>
                    <table className="table-fixed w-full h-full mt-5">
                        <tbody>

                        <tr className="border-b">
                            <td className="w-1/4 py-2"><strong>Listing state:</strong></td>
                            <td className="w-3/4 py-2">
                                <StateBadge state={listing?.currentState}/>
                                {isCurrentUserOwner && (
                                    <span className="mx-4" {...{theme: 'badge success'}}>This is your listing!</span>
                                )}
                            </td>
                        </tr>

                        <tr className="border-b">
                            <td className="w-1/4 py-2"><strong>Donor Name:</strong></td>
                            <td className="w-3/4 py-2">{listing?.donor?.name}</td>
                        </tr>

                        <tr className="border-b">
                            <td className="w-1/4 py-2"><strong>Donor Contacts:</strong></td>
                            <td className="w-3/4 py-2">
                                <p><a href={`mailto:${listing?.donor?.email}`}>{listing?.donor?.email}</a></p>
                                <p>{listing?.donor?.phone}</p>
                            </td>
                        </tr>

                        <tr className="border-b">
                            <td className="w-1/4 py-2"><strong>Pickup Location:</strong></td>
                            <td className="w-3/4 py-2">
                                <p>{listing?.pickupLocation}</p>
                            </td>
                        </tr>

                        {listing?.allergens?.length !== 0 && (
                            <tr className="border-b">
                                <td className="w-1/4 py-2"><strong>Dietary Properties:</strong></td>
                                <td className="w-3/4 py-2"> {listing?.allergens?.join(", ")}</td>
                            </tr>
                        )}

                        <tr className="border-b">
                            <td className="w-1/4 py-2"><strong>Description:</strong></td>
                            <td className="w-3/4 py-2">
                                <p>{listing?.description}</p>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </section>
                {listing?.currentClaimingUser && (
                    <section className="my-10">
                        <span className="text-2xl leading-none tracking-tight text-gray-900 md:text-5x">Claimant information</span>
                        <table className="table-fixed w-full h-full mt-5">
                            <tbody>
                            <tr className="border-b">
                                <td className="w-1/4 py-2"><strong>Name:</strong></td>
                                <td className="w-3/4 py-2">{listing.currentClaimingUser.name}</td>
                            </tr>

                            <tr className="border-b">
                                <td className="w-1/4 py-2"><strong>Contact:</strong></td>
                                <td className="w-3/4 py-2">
                                    <p><a href={`mailto:${listing.currentClaimingUser.email}`}>{listing.currentClaimingUser.email}</a></p>
                                    <p>{listing.currentClaimingUser.phone}</p>
                                </td>
                            </tr>

                            <tr className="border-b">
                                <td className="w-1/4 py-2"><strong>Claimed At:</strong></td>
                                <td className="w-3/4 py-2">{new Date(listing.claimTime as string).toUTCString()}</td>
                            </tr>
                            </tbody>
                        </table>
                    </section>
                )}
            </div>

            <Tabs slot="navbar touch-optimized" theme="minimal equal-width-tabs"
                  className="w-full bottom-0 lg:flex lg:justify-center lg:gap-6">
                <Tab aria-label="Back">
                    <NavLink to="/food-listings" tabIndex={-1}>
                    <Icon icon="vaadin:arrow-circle-left" className={iconStyle}/>
                    </NavLink>
                </Tab>

                {listing?.currentState == ClaimState.UNCLAIMED && !isCurrentUserOwner && (
                    <Tab aria-label="Claim">
                        <NavLink to="#" onClick={() => alert("Order claimed")} tabIndex={-1}>
                            <Icon icon="vaadin:location-arrow-circle" className={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                {isCurrentUserOwner && (
                    <Tab aria-label="Edit">
                        <NavLink to="#" onClick={() => alert("Editing listing")} tabIndex={-1}>
                            <Icon icon="vaadin:ellipsis-circle" className={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                {isCurrentUserOwner && (
                    <Tab aria-label="Cancel">
                        <NavLink to="#" onClick={() => setRemoveDialogOpened(true)} tabIndex={-1}>
                            <Icon icon="vaadin:close-circle" className={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}

                {isCurrentUserClaiming && (
                    <Tab aria-label="Remove Claim">
                        <NavLink to="#" onClick={() => setUnclaimDialogOpened(true)} tabIndex={-1}>
                            <Icon icon="vaadin:close-circle" className={iconStyle}/>
                        </NavLink>
                    </Tab>
                )}
            </Tabs>
        </>
    );
};