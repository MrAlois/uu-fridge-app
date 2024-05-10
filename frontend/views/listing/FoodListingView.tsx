import React, {useEffect, useState} from "react";
import Gallery from "Frontend/views/listing/Gallery";
import {NavLink} from "react-router-dom";
import FoodListingModel from "Frontend/generated/cz/asen/fridge/domain/FoodListingModel";
import {FoodListingEndpoint} from "Frontend/generated/endpoints";
import FoodListing from "Frontend/generated/cz/asen/fridge/domain/FoodListing";

export default function FoodListingView() {
    const [serverListings, setServerListings] = useState<FoodListing[]>([])
    useEffect(() => {
        FoodListingEndpoint.getAllListings().then((result) => {
            let listings = result == undefined ? [] as FoodListing[] : result?.filter(it => it != null)
            setServerListings(listings as FoodListing[])
        })
    }, []);

    function shortenText(input: string) {
        return input.length > 20 ? `${input.substring(0, 20)}...` : input;
    }

    const truncate = (input: string | undefined) => input == null ? "" : shortenText(input);

    return (
        <div className="mx-auto px-4 sm:px-0 w-full">
            <input
                className="w-full lg:max-w-2xl p-2 mt-6 mb-4 border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                type="text"
                placeholder="Search Listings..."
            />

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4 justify-items-center">
                {serverListings.map((listing: FoodListing) => (
                    <div key={listing.id}
                         className="flex flex-col border overflow-hidden rounded-md items-stretch w-full max-w-xs p-4">
                        <div className="flex justify-between">
                            <NavLink to={`/food-listings/${listing.id}`}>
                                <h4 className="font-semibold">{truncate(listing.shortDescription)}</h4>
                            </NavLink>
                            <p className="text-sm text-gray-500">{new Date(listing?.created as string).toDateString()}</p>
                        </div>

                        <Gallery images={listing?.base64Images} />

                        <div className="text-sm">
                            <p className="mb-2"><strong>Donor:</strong> {listing?.donor?.name}</p>
                            <p className="mb-2"><strong>Expires on:</strong> {new Date(listing?.expiryDate as string).toDateString()}</p>
                            <p className="mb-2"><strong>Address:</strong> {listing.pickupLocation}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}