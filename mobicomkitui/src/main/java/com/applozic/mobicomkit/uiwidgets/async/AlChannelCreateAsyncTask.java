package com.applozic.mobicomkit.uiwidgets.async;

import android.content.Context;
import android.os.AsyncTask;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.channel.ChannelMetadata;

/**
 * Created by Sunil on 12/26/2016.
 */

public class AlChannelCreateAsyncTask extends AsyncTask<Void, Void, ChannelFeedApiResponse> {
    Context context;
    ChannelService channelService;
    ChannelInfo channelInfo;
    TaskListenerInterface taskListenerInterface;

    public AlChannelCreateAsyncTask(Context context, ChannelInfo channelInfo, TaskListenerInterface taskListenerInterface) {
        this.context = context;
        this.taskListenerInterface = taskListenerInterface;

        setChannelMetaData(channelInfo);

        this.channelInfo = channelInfo;
        this.channelService = ChannelService.getInstance(context);
    }

    @Override
    protected ChannelFeedApiResponse doInBackground(Void... voids) {
        if (channelInfo != null) {
            return channelService.createChannelWithResponse(channelInfo);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ChannelFeedApiResponse channelFeedApiResponse) {
        super.onPostExecute(channelFeedApiResponse);
        if (channelFeedApiResponse != null) {
            if (channelFeedApiResponse.isSuccess()) {
                taskListenerInterface.onSuccess(channelService.getChannel(channelFeedApiResponse.getResponse()), context);
            } else {
                taskListenerInterface.onFailure(channelFeedApiResponse, context);
            }
        } else {
            taskListenerInterface.onFailure(channelFeedApiResponse, context);
        }
    }

    public interface TaskListenerInterface {
        void onSuccess(Channel channel, Context context);

        void onFailure(ChannelFeedApiResponse channelFeedApiResponse, Context context);
    }

    private void setChannelMetaData(ChannelInfo channelInfo){
        ChannelMetadata channelMetadata = new ChannelMetadata();
        channelMetadata.setCreateGroupMessage(ChannelMetadata.ADMIN_NAME + " heeft de groep " + ChannelMetadata.GROUP_NAME + " aangemaakt.");
        channelMetadata.setAddMemberMessage(ChannelMetadata.ADMIN_NAME + " heeft " + ChannelMetadata.USER_NAME + " toegevoegd.");
        channelMetadata.setRemoveMemberMessage(ChannelMetadata.ADMIN_NAME + " heeft " + ChannelMetadata.USER_NAME + " verwijderd.");
        channelMetadata.setGroupNameChangeMessage(ChannelMetadata.USER_NAME + " heeft de groepsnaam veranderd naar " + ChannelMetadata.GROUP_NAME);
        channelMetadata.setJoinMemberMessage(ChannelMetadata.USER_NAME + " is toegetreden tot de groep");
        channelMetadata.setGroupLeftMessage(ChannelMetadata.USER_NAME + " heeft de groep " + ChannelMetadata.GROUP_NAME + " verlaten.");
        channelMetadata.setGroupIconChangeMessage(ChannelMetadata.USER_NAME + " heeft de groepsafbeelding aangepast.");
        channelMetadata.setDeletedGroupMessage(ChannelMetadata.ADMIN_NAME + " heeft de groep " + ChannelMetadata.GROUP_NAME + " verwijderd.");

        channelInfo.setChannelMetadata(channelMetadata);
    }
}
